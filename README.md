# AutoDelete
Watcher service that deletes certain files as soon as they are created in a directory.

This service makes use of the notification APIs.

## Implementation
This has been developed as a Netbeans project.

We use Java's WatcherService facilities.

Note that the underlying Linux inotify facility does not work through
NFS according to my tests.  The NFS server probably does not send
notifications.

## License

GNU General Public License, either version 3 of the license, or (at
your option) any later version.
