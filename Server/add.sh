#!/bin/bash
CHECK=$(cat "score/$1" | grep "$2" | awk '{print $2}')
echo $CHECK
if [ "$CHECK" == "" ]
then
    echo "$2 $3" >> "score/$1"
    sort -k 2 -r -n "score/$1" > txt.in
    cat txt.in > "score/$1"
elif  [ $3 -gt $CHECK ]
then
    cat "score/$1" | awk -v name="$2" -v score="$3" '{
        if ($1 == name) {
            print name " " score
        }
        else {
            print $1 " " $2
        }
    }' > txt.in
    sort -k 2 -r -n txt.in > "score/$1"
fi