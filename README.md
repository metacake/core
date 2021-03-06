# Cake, the Meta game engine #
"You can have your Cake and eat it too."

[![Build Status](https://travis-ci.org/metacake/core.png?branch=dev)](https://travis-ci.org/metacake/core)

## Mission Statement ##
The goal of Cake is to provide a pluggable cross platform framework for creating games and game engines.

## The Cake Metaphor ##
The Cake Engine is very much like a real cake. Besides being awesome and delicious&#42;, it has layers and icing. What makes the Cake Engine different from most real cakes is that you get to choose what kind of cake it is. We provide you with some layers of vanilla engine implementations, but maybe you don't want vanilla. Maybe you like chocolate or red velvet. Maybe you only want some of the layers to be vanilla, but others to be chocolate.
 
With the Cake Engine, you can have your vanilla-chocolate-strawberry layered cake with Mocha Buttercream icing. We provide interfaces for each layer of the cake, so that you can plug any layer into the cake and will interact with the other layers. This is how the Cake engine lets you build your own engine. 

Maybe the vanilla layers are just fine for you; you just want to make a game. You can do that with Cake too. We call this adding "icing to the cake." All you have to do is implement the interfaces for the game, add in some logic and artwork, and dig in&#42;&#42;.


&#42; Please don't actually try to eat it.

&#42;&#42; Really, don't eat it. Just have fun playing.

## Project Dependencies ##
Dependencies:
+ Java 8 Build b106 (Note: This is very much subject to change, as we will continue to move to more stable versions of Java 8)
+ Maven 3 (Note: We are waiting for Groovy/Gradle to support Java 8)

## Building CAKE ##
The CAKE framework can be built with Maven. Running the following command at the root of the project will build it:
```
mvn clean install
```
