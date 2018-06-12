# GM-SIS TEAM SE10 ECS506U

![GM-SIS graphic](GM-SIS/src/gmsis/ui/gmsis-splash.png)


## Requirements

To run GM-SIS, you will need Java 8, minimum version 1.8u60 (higher on Windows) with either Oracle JavaFX Runtime or OpenJFX Runtime.
A copy of Oracle's JRE 1.8u121 is provided in the release .tar.xz file for both Windows and Linux.

## Troubleshooting installation

At present, the state of the Windows version of the JRE is dire - JavaFX is broken as per
[my research](http://qmplus.qmul.ac.uk/mod/forum/discuss.php?d=127475).
If you cannot run gmsis.jar, or the application crashes or hangs when a combo box or dropdown
is opened, and the submission that came through the coursework submission
system does not include any JREs due to space constraints, you can either download a
"fully-loaded" version [from GitHub](https://github.research.its.qmul.ac.uk/ecs506u/SE10/releases),
or download a .tar.gz JRE appropriate for your system
[from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html),
and use it to run gmsis.jar (if you untar it to the directory containing gmsis.jar,
use instructions below on running with a bundled JRE on Windows, but remember to update the
path to `java.exe`).

## Running GM-SIS

The following commands assume you are in the directory containing `gmsis.jar`.

To run GM-SIS with your system Java, use the following command:

```bash
java -jar gmsis.jar
```

To run GM-SIS with the bundled JRE on Linux, use the following command:

```bash
./launch-gmsis.sh
# Equivalent to below but also updates JAVA_HOME and PATH for that run
# ./jre1.8.0_121-linux/bin/java -jar gmsis.jar
```

To run GM-SIS with the bundled JRE on Windows, use the following command:

```bash
./jre1.8.0_121-windows/bin/java.exe -jar gmsis.jar
```

(Sorry, no launch script - PowerShell is disabled on our Windows profiles)

Admin Access: ID- 0001 Password- admin

## Licence

There is none. Queen Mary owns our souls anyway.
