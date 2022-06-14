#!/bin/bash
cat "score/$1" | awk -v name=$2 '
    BEGIN{
        i = 0;
    } {
        i++;
        if ($1 == name) {
            print $2

        }
    }
' > txt.in

CHECK=$(cat txt.in)

if [ "$CHECK" == "" ]
then
    echo "0"
else
    echo $CHECK
fi

rm txt.in