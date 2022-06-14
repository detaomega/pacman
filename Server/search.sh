#!/bin/bash
sed -n "$2,$2p" "score/$1" | awk '{print $1; print$2;}'