import sys
import math
from collections import deque
from itertools import count

example1 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\20-12-2023\\example1.txt"
example2 = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\20-12-2023\\example2.txt"
inputText = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\20-12-2023\\input.txt"

def propagate_pulse(graph, flops, conjs, sender, receiver, pulse):
	if receiver in flops:
		if pulse:
			return
		next_pulse = flops[receiver] = not flops[receiver]
	elif receiver in conjs:
		conjs[receiver][sender] = pulse
		next_pulse = not all(conjs[receiver].values())
	elif receiver in graph:
		next_pulse = pulse
	else:
		return

	for new_receiver in graph[receiver]:
		yield receiver, new_receiver, next_pulse

def run(graph, flops, conjs):
	q = deque([('button', 'broadcaster', False)])
	nhi = nlo = 0

	while q:
		sender, receiver, pulse = q.popleft()
		nhi += pulse
		nlo += not pulse
		q.extend(propagate_pulse(graph, flops, conjs, sender, receiver, pulse))

	return nhi, nlo

def find_periods(graph, flops, conjs):
	periodic = set()

	for rx_source, dests in graph.items():
		if dests == ['rx']:
			assert rx_source in conjs
			break

	for source, dests in graph.items():
		if rx_source in dests:
			assert source in conjs
			periodic.add(source)

	for iteration in count(1):
		q = deque([('button', 'broadcaster', False)])

		while q:
			sender, receiver, pulse = q.popleft()

			if not pulse:
				if receiver in periodic:
					yield iteration

					periodic.discard(receiver)
					if not periodic:
						return

			q.extend(propagate_pulse(graph, flops, conjs, sender, receiver, pulse))

fin = open(inputText, 'r')

flops = {}
conjs = {}
graph = {}
iterations = []

with fin:
	for line in fin:
		source, dests = line.split('->')
		source = source.strip()
		dests = dests.strip().split(', ')

		if source[0] == '%':
			source = source[1:]
			flops[source] = False
		elif source[0] == '&':
			source = source[1:]
			conjs[source] = {}

		graph[source] = dests

for source, dests in graph.items():
	for dest in filter(conjs.__contains__, dests):
		conjs[dest][source] = False

tothi = totlo = 0
for _ in range(1000):
	nhi, nlo = run(graph, flops, conjs)
	tothi += nhi
	totlo += nlo

answer1 = totlo * tothi
print('Part 1:', answer1)
p = math.prod(find_periods(graph,flops,conjs))
g = 1
for i in find_periods(graph,flops,conjs):
    g = math.gcd(g,i)
answer2 = p / g
print('Part 2: ', answer2)