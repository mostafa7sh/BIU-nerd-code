# part2 of the homework
splitter () {
counter=1
lines=$(wc -l < $1)
echo "Metadata from PGN file:"
while [ "$counter" -le "$lines" ]
do
	line=$(head -$counter $1 | tail -1)
	echo "$line"
if [ -z "$line" ]
then
break
fi

((counter++))
done

rest=$[ "$lines" - "$counter" + 1 ]
line=$(cat $1 | tail -$rest)
python3 parse_moves.py "$line" > ./help.txt
cat ./help.txt | tr '87654321' '01234567' > ./simple_help.txt
cat ./simple_help.txt | tr 'abcdefgh' '01234567' > ./another_help.txt

num=0
for move in $(python3 parse_moves.py "$line")
do
	moves[$num]=$move
	((num++))
done

# board creating
for ((i = 0; i < 64; i++))
do
	board[$i]="."
done

for ((i = 0; i < 8; i++))
do
	small=$((i + 8))
	cap=$((i + 48))
	board[$small]="p"
	board[$cap]="P"
done

board[0]="r"
board[1]="n"
board[2]="b"
board[3]="q"
board[4]="k"
board[5]="b"
board[6]="n"
board[7]="r"

board[56]="R"
board[57]="N"
board[58]="B"
board[59]="Q"
board[60]="K"
board[61]="B"
board[62]="N"
board[63]="R"

# board printing
echo "Move 0/$num"
echo "  a b c d e f g h"

counter=0
k=8
for ((i = 0; i < 8; i++))
do
	sub=$((k - i))
	echo -n "$sub "
for ((j = 0; j < 8; j++))
do
	echo -n "${board[$counter]} "
	((counter++))
done
echo "$sub"
done
echo "  a b c d e f g h"

}

# the function that runs the moves on the chess board
run () {
# board creating
for ((i = 0; i < 64; i++))
do
	board[$i]="."
done

for ((i = 0; i < 8; i++))
do
	small=$((i + 8))
	cap=$((i + 48))
	board[$small]="p"
	board[$cap]="P"
done

board[0]="r"
board[1]="n"
board[2]="b"
board[3]="q"
board[4]="k"
board[5]="b"
board[6]="n"
board[7]="r"

board[56]="R"
board[57]="N"
board[58]="B"
board[59]="Q"
board[60]="K"
board[61]="B"
board[62]="N"
board[63]="R"

# run the moves on the board
line=$(cat ./another_help.txt)
for ((i = 0; i < $1; i++))
do
    k=$[$i + 1]
    word=$(echo $line | cut -d' ' -f$k)

    # checking for castling at first
    if [ $word = "4767" ]; then
        board[60]="."
        board[62]="K"
        board[63]="."
        board[61]="R"
    elif [ $word = "4727" ]; then
        board[60]="."
        board[58]="K"
        board[56]="."
        board[59]="R"
    elif [ $word = "4060" ]; then
        board[4]="."
        board[6]="k"
        board[7]="."
        board[5]="r"
    elif [ $word = "4020" ]; then
        board[4]="."
		board[2]="k"
        board[0]="."
        board[3]="r"
    else

		char1=$[${word:0:1}]
    	char2=$[${word:1:1}]
		char3=$[${word:2:1}]
    	char4=$[${word:3:1}]

    	before=$[$char2 * 8 + $char1]
    	after=$[$char4 * 8 + $char3]
	
        char=${board[$before]}
        board[$before]="."
        board[$after]=$char

        # now I will check for promotion
        if [ $(echo -n $word | wc -m) -eq 5 ]; then
            # mean that "p" (small letter) reached the other side of the board
            if [ ${board[$after]} = "p" ]; then
                if [ ${word:4:1} = "q" ]; then
                    board[$after]="q"
                elif [ ${word:4:1} = "r" ]; then
                    board[$after]="r"
                elif [ ${word:4:1} = "b" ]; then
                    board[$after]="b"
                elif [ ${word:4:1} = "n" ]; then
                    board[$after]="n"
                fi
            # mean that "P" (capital letter) reached the other side of the board
            elif [ ${board[$after]} = "P" ]; then
                if [ ${word:4:1} = "q" ]; then
                    board[$after]="Q"
                elif [ ${word:4:1} = "r" ]; then
                    board[$after]="R"
                elif [ ${word:4:1} = "b" ]; then
                    board[$after]="B"
                elif [ ${word:4:1} = "n" ]; then
                    board[$after]="N"
                fi
            fi
        fi
    fi
done


# board printing
echo "Move $1/$num"
echo "  a b c d e f g h"

counter=0
k=8
for ((i = 0; i < 8; i++))
do
	sub=$((k - i))
	echo -n "$sub "
for ((j = 0; j < 8; j++))
do
	echo -n "${board[$counter]} "
	((counter++))
done
echo "$sub"
done
echo "  a b c d e f g h"
}


key=a
index=0
if [ ! -e $1 ]
then
echo "File does not exist: $1"
exit 1
fi
splitter "$1"
# num: is the number of the moves, its like max index whenever use input 's'

while [ ! $key = "q" ]
do
echo "Press 'd' to move forward, 'a' to move back, 'w' to go to the start, 's' to go to the end, 'q' to quit:"
read key

if [ $key = "q" ]
then

rm ./help.txt ./another_help.txt ./simple_help.txt
echo "Exiting."
echo "End of game."
exit 0

elif [ $key = "w" ]
then
index=0
run "$index"

elif [ $key = "s" ]
then
index=$[$num]
run "$index"

elif [ $key = "a" ]
then
if [ $index -eq 0 ]
then
run "$index"
else
index=$[$index - 1]
run "$index"
fi

elif [ $key = "d" ]
then
if [ $index -eq $num ]
then
echo "No more moves available."
else
index=$[$index + 1]
run "$index"
fi

else
echo "Invalid key pressed: $key"
fi

done

