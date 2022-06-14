#!/bin/bash
echo "$2 $3" >> "score/$1"
sort -k 2 -r -n $(echo "score/$1") > test.in
cat test.in > "score/$1"