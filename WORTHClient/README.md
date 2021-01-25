## Getting Started

Welcome to WORTHClient
## Folder Structure

The workspace contains many folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `bin`: the folder to contain all binary files as .Class files

## Compile and Execute

You must launch the WorthServer process before launching the client, otherwise
ConnectionException will be thrown.
You can compile the project with two different Operating Systems:
	- `Windows` : Open a Powershell and change your current position to WORTHClient then
		      execute this script : ./Compile.ps1
	- `Linux` : Open a Terminale and change your current position to WORTHlient then 
 		      execute this script: bash Compile.sh
For executing you should do the same procedure, however you have to substitute in both operating system the script Compile with Execute.
	 