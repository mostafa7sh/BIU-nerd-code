# our script accepts 2 agrguments: source PGN file and Destination directory.
main () {
dir=$(basename "$2")
filename=$(basename "$1" .pgn)
lines=$(wc -l < $1)
line=meow
enter=0
file=1
counter=1
num1=0
while [ "$counter" -le "$lines" ]
do
        line=$(head -$counter $1 | tail -1)
	((counter++))

if [ -z "$line" ]
then
    ((enter++))
fi

if [ "$enter" -eq 2 ]
then
	((counter++))
	echo "Saved game to $dir/"$filename"_$file.pgn"
	enter=0
	((file++))
	line=$(head -$counter $1 | tail -1)
	((counter++))
fi
	echo "$line" >> "$dir/"$filename"_$file.pgn"
done

echo "Saved game to $dir/$filename_$file.pgn"
}

# Argument Validation
if [ -n "$1" -a -n "$2" -a -z "$3" ]
then
# File Existence Check
if test -e "$1"
then

# Directory Validation & Creation
if test -d "$2"
then
	dir=$(basename "$2")
	main $1 $2
	echo "All games have been split and saved to '$dir'"
else
	mkdir ./$2
	echo "Created directory '$dir'."
	main $1 $2
	echo "All games have been split and saved to '$dir'"
fi
#ends here Directory Validation & Creation

else
	echo "Error: File '$1' does not exist."
	exit 1
fi
# ends here File Existence Check

else
	echo "Usage: $0 <source_pgn_file> <destination_directory>"
	exit 1
fi
