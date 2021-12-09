# Program 2

## step 1

read a set of a paragraph of 6 sentences from a file

choose txt file to store the paragraph

use **IN instruction** to read from txt file through devid

each memory unit will store a single character (ascii)

space (ascii: Dec32) will divide the word

. ? !(ascii: Dec46, Dec33 Dec63) will divide the sentence

## step 2

print the sentences on the console printer

use **OUT instruction** to print the paragraph through loop

## step 3

ask user for a word

use **IN instruction** to ask user to input the word by inputting a single character each time and end up with space

## step 4

search target from the paragraph

X1 is used for search paragraph

X2 is used for get next character in target

1 memory unit stores the value in X1

1 memory unit stores the value in X2

several constant memory units store the target which ends with space (ascii: Oct.40)

1 memory unit stores the word number (using X1 to index)

​    start from 1, each time meet a space in paragraph, word number + 1. when the sentence ends, word number = 1

1 memory unit stores the sentence number (using X2 to index)

​    start from 1, each time meet . ? ! sentence number + 1

## step 5

print out the word, sentence number, and the word number in the sentence.



1. modify program1 in project 2(donot use memory at location 1 and 2 to store the IX)

2. realize trap instruction

3. implement read paragraph from txt function