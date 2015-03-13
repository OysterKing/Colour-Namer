# Colour-Namer
This program takes the colours tweeted by everycolourbot.
Names them by looking at the hexadecimal colour code generated and matching a database of named, ready made colours.
Note that the first few commits will be large chunks of code as I didn't commit as I was writing the code (much to my regret).

##11/03/2015 Commit 1:
The ColourCalculator class reads in a txt file of colours. 
The colours are stored in a hash map where the name of the colour is the key and the hexadecimal code is the value. 
For example; acid green #B0BF1A is stored in the format:

Key: acid green (separated by a tab).
value: #B0BF1A

I used the colour names as the keys for each colour as some colour names have the same hex codes.

The ColourCalculator class provides several useful methods that will be used later to create an inventory of ready made colours:

getColourCode(String colourKey) returns the hex code for a colour name.

getRedValue(String rgb) returns the red value for a given hexcode (returns two character hexcode).

getGreenValue(String rgb) returns the green value for a given hexcode (returns two character hexcode).

getBlueValue(String rgb) returns the blue value for a given hexcode (returns two character hexcode).

getAllColoursOf(String colour) takes the first part of a colour name (e.g. leaf).
Returns a vector containing the hexcodes of each shade of that colour.
For example getAllColoursOf(apple) would return a vector containing two hex codes, one red and one green.

getAllShadesOf(String colour) takes the second part of a colour name (e.g. green).
Returns a vector containing the names of each shade of that colour.

getShadeOf(String colour) takes the second part of a colour name and returns a vector containing the name of a shade of that colour.

blend(String colourKey1, String colourKey2, double weightOfMix) takes two colour codes and mixes them using the algorithm:
r3 = weight * r1 + (1 - weight) * r2
Returns a new hexcode that is a mix of colour 1 and colour 2.

distance(String hexcode1, String hexcode2) takes two colour codes and uses euclidian distance to find the distance between them.
Consider that each colour code is a point in space where red = x, green = y and blue = z then:
distance = sqrt((x1 - x2)^2 + (y1 - y2)^2 + (z1 - z2)^2)
Returns an integer.

##13/03/2015 Commit 2:
The ReadyMadeInventory class reads in two txt files. One file contains a list of bigrams such as ocean wood.
The other file contains a list of unigrams such as goldfire. 
All the bigrams and unigrams contain words that appear in the hashmap contained in the ColourCalculator class.
The ReadyMadeInventory class constructor takes an instance of ColourCalculator class as an argument and initializes a variable with it.
It then calls two private methods; loadBigrams() and loadUnigrams() respectively.

The loadBigrams() function, splits all the bigrams in two and searches the colourMap hashmap, contained in ColourCalculator,
for the all the shades that correspond to those words, storing them in two separate vectors, 
(one for the modifier and one for the head). 
It then calls another private function called createRGBCodes that takes the two vecotrs as arguments and iterates through them both,
calling the ColourCalculator's blend function for each colour.
Each colour code that the blend function returns is saved in a vector which is then returned to the loadBigrams() function.
Then the loadBigrams() function saves the bigram and the vector returned from createRGBCodes into a hashmap as key and value respectively.

The loadunigrams() function does the same thing as the loadBigrams() function only it has to iterate through the unigram,
character by character, until it finds a word that matches a word in the colourMap hashmap.

The private method linguisticRules(String modifier, String head) is used to decide the weight of the mixture of two colours.
If the head is a colour such as white or blue, the mixture is set to 0.35 (0.35 of mod, 0.65 of head).
Otherwise it is set to 0.5, a 50:50 mix.

