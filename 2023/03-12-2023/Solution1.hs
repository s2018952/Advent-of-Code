{-# LANGUAGE TemplateHaskell #-}
module Solution1 where

import Data.List
import Data.Char

examplePath :: String
examplePath = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\03-12-2023\\example.txt"

inputPath :: String
inputPath = "C:\\Users\\nihak\\Documents\\Advent of Code\\2023\\03-12-2023\\input.txt"

splitLineToCells :: String -> [String]
splitLineToCells line = [[c] | c <- line]

splitLines :: [String] -> [[String]]
splitLines = map splitLineToCells

getNumStrFromLine :: String -> [String]
getNumStrFromLine = filter (isDigit . head) . groupBy (\x y -> isDigit x && isDigit y)