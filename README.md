# RSS-feed-search

After the user enters an url, the program searches in the header section for a
link tag with the type "application/rss+xml". After that the program got the
link for the rss server. With "saxt" the program is parsing the xml into a java
class, which contains custom annotations for dynamic parsing.