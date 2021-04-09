# My Personal Project

## Proposal

### What will the application do?
This application will generate a list of randomized DND Monsters.
The user will be able to specify a type and level if they want to, and then the program will generate from there.
Included with the program will be a text file with names and types of creature.
Along with randomized identity,
the program will generate stats like armor strength (AC) and Dexterity.

### Who will use it?
This application assists Dungeon Masters (DMs) in the often tedious and time-consuming monster creation process.
Monsters are necessary for every session (often multiple) and this program will aid a DM's creative process.
They will be able to create a monster and then change aspects of the monster. This will provide a starting point for
DMs.

### Why is this project of interest to you?
I recently tried to start writing a one-shot (single session DND campaign), but got lost in monster creation.
I also believe that it would help my friends to write their own one-shots if they had access to a program that generated
monsters.
In short, this project is of interest to me because it would help me participate in one of my hobbies.

## User Stories

- As a user, I want to be able to add multiple Monsters to a Monster list
- As a user, I want to be able to hit a button and generate a new monster based on monster level and type
- As a user, I want to be able to generate a large amount of random monsters, so I can look through and find one I like
- As a user, I want to be able to view a list of monsters I've previously generated
- As a user, I want to be able to select a monster to view the attributes of the monster
- As a user, I want to be able to edit the attributes of a monster that has been generated
- As a user, I want to be able to 'favourite' some monsters and see a list of my favourite monsters
- As a user, I want to be able to delete monsters from the list
- As a user, I want to be able to save all of my monsters to a file
- As a user, I want to be able to save only my favourite monsters to a file
- As a user, I want to be able to load all of my monsters from a file and add those to any monsters I currently have

### Phase 4: Task 2
The MonsterGroup class has a robust design. The method getFavourites() throws a NoFavouriteMonstersException if there
are no favourite monsters in the monster group. All other methods can take any valid parameter and function as intended.

### Phase 4: Task 3
The UML diagram presents a few cases of redundancy. A few classes that directly use the monster class don't need
to. The class MonsterGroupInterfaceHelper was designed to serve as a middleman between the MonsterListFrame and Monster,
however there were some methods in the monsterListFrame that needed to save a monster. The program could be refactored
so that the MonsterListFrame does not access the Monster class as intended. MonsterDisplayFramework could also be
refactored so that it does not directly use the monster class. Note that the only methods in Monster used by other
classes are getters, setters, and the constructor. 

Aside from these two issues, the UML class design diagram is fairly simple. Each class adheres to the SRP. Each class
has a clear purpose. This supports my statements in the previous paragraph as the refactoring I would do is not about
the content of each class, rather the internal mechanics of each class.

Another feature of my code that supports its good design is that in order to implement the User Interface, I did not
have to change any of the implementation in the Monster/MonsterGroup class. I purely added classes to the UI package
that deal specifically with handling user inputs and the display of monsters. The main/core functionality is independent
of how the user interacts with the system.



