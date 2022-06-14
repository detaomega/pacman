#!/bin/bash
cat "score/$1" | awk -v name=$2 '
    BEGIN{
        i = 0;
    } {
        i++;
        if ($1 == name) {
            print i

        }
    }
'