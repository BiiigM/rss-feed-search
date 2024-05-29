# RSS-feed-search

After the user enters an url, the program searches in the header section for a
link tag with the type "application/rss+xml". After that the program got the
link for the rss server. With "saxt" the program is parsing the xml into a java
class, which contains custom annotations for dynamic parsing.

## Importen Info
I used this project to play around with annotations. In a "real" project you 
should use a library like [Jakson](https://github.com/FasterXML/jackson-dataformat-xml).
But if you use your own reflection logic I do, you should do checks for all
possible exceptions (not like I do).
