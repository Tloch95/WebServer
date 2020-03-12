# CSC 335: Webserver

Launching a browser and visiting a site on the Internet involves at least two parties: 
the web server and the requestor (you). In this lab, you will use a Java ``ServerSockets``
to implement a primitive web server, which talks HTTP to browsers over TCP/IP.

HTTP (HyperText Transfer Protocol) is a simple, text-based communication protocol by 
which a web browser requests documents from a web server, and the web server replies. 
For instance, if you visit a web site such as http://www.example.com/test.html, 
your browser does the following:

1.	Connect to the IP address of http://www.example.com/test.html (obtained via DNS lookup) at port 80 (standard web server port)

2.	Sends the HTTP request message:

```
GET /test.html HTTP/1.1
Host: www.example.com
```

To which the server should reply (assuming it finds the file):

```
HTTP/1.1 200 OK
Date: Thu, 15 Nov 2007 00:24:55 GMT
Content-Length: 4892
Connection: close
Content-Type: text/html
```

Followed by a blank line and the contents of the file (which is 4892 bytes in this example). 

If the file is not found, it should return the infamous 404 error code like so:

```
HTTP/1.1 404 Not Found
```

Note that this message should also be followed by a blank line.

## Requirements

Your task is to use a ServerSockets to accept GET requests for HTML pages over HTTP. 
Your program should wait for a connection to occur and communicate to the requestor 
according to the above protocol.

## Ports and Addresses
Port 80 is the normal web server port, but we can’t bind it without being an administrator. We can use a port > 1023, such as port 4000. Our url will then be localhost:4000

## Hints and Requirements
- The ``Socket``'s ``InputStream`` and ``OutputStream`` are byte-oriented streams. We will only 
deal with text files for our simple server, so we can use the adapter classes ``OutputStreamWriter`` 
and ``InputStreamReader`` to turn them into character-oriented streams that we can then wrap as 
``BufferedReader`` and ``PrintWriter``

- Use the ``File`` class to determine if the files exist or not. You can also use it to get the 
length of the file.

- After the blank line of the response, dump the entire contents of the file to the output stream.

## Testing

You should be able to open up your favorite web browser and go to the url localhost:4000/test.html and 
if test.html exists in the root of the eclipse project (I've provided a simple page to test), see its contents in the browser.

If you need help debugging, you can see what the server sent to the browser by right clicking the page
and saying "inspect" and selecting the network inspector tab. 
