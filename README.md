# clj-curl

curl for clojure using JNA, because we love curl.

[![Clojars Project](https://img.shields.io/clojars/v/clj-curl.svg)](https://clojars.org/clj-curl)

## But... why?

Clojure definitely has excellent libraries to handle http connections like clj-http and other things, however, none of them are complete as curl.
There is a very good reason why curl has been ported for almost every programming language there is, because curl is just awesome.
Curl supports a lot of protocols, like HTTP(S), FTP(S), SFTP, IMAP(S), POP3(S), TFTP, SMB, SCP, RTMP, TELNET and more, checkout [protocols list](https://ec.haxx.se/protocols-curl.html) for the full list.
The curl's easy API is really easy. This sentence is not redundant as it appear, curl easy API is easy to use even with pure C, and it is so simple and well done that can be easily ported to other programming languages.

This library tries to be as close as possible to the C API. In case of doubts go to the examples folder and the official libcurl docs.

## Usage

The usage of this library is basically the same as the libcurl in C.
Everything is mutable, so be careful. This library is just about exposing the C api to the clojure world, without adding a lot of abstractions to make things easier or doing stuff the '''clojure way'''.
Just create a curl handler wrapped in a function without exposing the curl's state to your applications and you should be fine.

Deprecated functions like `curl_formadd` will not be added here, you could implement them yourself, however if you see the official docs saying to not use a function, do yourself a favor and not use it.

## Handlers? What are they??

libcurl depends A LOT in C callbacks to do stuff (mostly reading and writing).
The way that the JNA creates a '''c callback''' is creating a class that implements the `Callback` interface which you should define the contents of the inherited `callback` function.
I've created some handlers to perform some more basic and common stuff, like reading/writing from memory and reading/writing from/to files.
Every place that the C API asks for a C callback you should instantiate one of the Handler classes and pass its instance as a argument to `setopt`.
Please check the examples folder to see how to use and import them.
They are `gen-class` java classes, so you should import them always with the `import` clause, not `require`.

Also, besides this java handlers you could use the libc `fwrite` and `fread` to read and write to/from files, that is not recommended by the official docs because the WRITEDATA/READDATA shows some random crashes on windows. On other systems you should be fine.

## Docs

There is nothing much to doc here, because we are just exposing the C curl API.
99% of the time you will be setting some options to `setopt`, passing some handlers to read/write data and exec a `perform`.
The real useful docs are the official ones found at [official docs](https://curl.haxx.se/libcurl/c/), it should be easy enough to read a C program and port it to the clojure interface.

## TODO list

* `multi` interface
* finish the `mime` namespace
* add the `global` namespace
 
## License

Copyright © 2019

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
