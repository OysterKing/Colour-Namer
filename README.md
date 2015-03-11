# Colour-Namer
This program takes the colours tweeted by everycolourbot.
Names them by looking at the hexadecimal colour code generated and matching a database of named, ready made colours.
Note that the first few commits will be large chunks of code as I didn't commit as I was writing the code (much to my regret).

##Commit 1:
The ColourCalculator class reads in a txt file of colours. 
The colours are stored in a hash map where the name of the colour is the key and the hexadecimal code is the value. 
For example; acid green #B0BF1A is stored in the format:

Key: acid green (separated by a tab)
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
