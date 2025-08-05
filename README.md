## Text Restorer

This program fixes broken English text with the following problems:
- Some letters are changed to `'*'`
- Letters in words are mixed up
- All spaces are gone

## Example:
Input:  `A***ew*sbegninignt*g*tv***tried*f*s***ing*y*e*srtseionthebnkaadnofvhaingntohnigtod*`

Output: `Alice was beginning to get very tired of sitting by her sister on the bank and of having nothing to do`

## How it works:
- Finds words that match patterns with `'*'`
- Finds words with mixed letters
- Splits long text into separate words
- Picks the most common words when there are choices

## Usage:
1. Place a dictionary file at `'src/words'` (one word per line)
2. Compile and run the program
3. Restored text will be printed in the console

## Personal Notes:
Special thanks to Become a Developer program by PortaOne Learning Center for providing this interesting and challenging task!
