#!/bin/bash
CHECK=$(cat "score/$1" | grep "$2" | awk '{print $2}')
if [ "$CHECK" == "" ]
then
    echo "0"
else
    echo $CHECK
fi