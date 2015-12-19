# A Math Toolkit for Java Developers
Author: Joe Yanik (<yanikjoe@emporia.edu>)

The JOMA Developers' Area can provide a real service to the mathematical developer community by

1. providing a single source that developers can go to in search of code that they can use and
2. encouraging developers to create code in a way that would be the most useful.

The first step in this process is to generate a discussion about how this should take place. I would like to launch that discussion by sharing some thoughts about what developers can do to make their code most useful to others and by offering my own vision of one direction that this might take. Finally, I will describe a prototype that I have developed. In short, I propose a coordinated effort by developers to construct a Math Toolkit that would provide, in a single download, Java files that could be used to accomplish most of the common tasks required by mathematical Java developers.

## The Problem

Almost anyone who attempts to learn Java for the purpose of teaching mathematics encounters a familiar set of challenges. First, there is the chore of learning the language itself. This can be a daunting task, particularly for those who are unfamiliar with the object-oriented paradigm that was embraced so enthusiastically by the creators of Java. The second challenge is becoming familiar with the vast libraries that come with Java. These contain some very powerful and well-documented tools, but it requires a bit of background to be able to use them correctly.

In addition to these challenges, though, mathematicians face some special obstacles. The first one is encountered very early: It is the problem of allowing for numerical input by the user. The fact that all input comes in the form of a String of characters that must be converted into a numerical data type can be a troublesome inconvenience when trying to write even the simplest mathematics program. Java does provide (literally!) methods for doing this when the String represents a number, but that doesn't solve the problem of allowing for numerical expressions such as "3*4" or "Pi^2".

The second obstacle encountered by almost every mathematical developer is the problem of displaying graphs. Again, there are some useful tools built into the language, but it is soon clear that they were not designed with mathematicians in mind. For example, there is a coordinate system, but, as with other programming languages, the coordinates are in pixel units, which means that all coordinates must be integers. As if that weren't enough of an outrage, the y-axis is upside down and the origin is in the upper left hand corner!

When faced with these and other obstacles, some mathematicians relish the challenge and take this as an opportunity to delve deeply into the Java programming language in order to come up with individual solutions. Others search far and wide for Java code that they can copy into their own programs. I fear that there may be still others who give up entirely.

What we need is a library of tools that can be used by both novice and experienced programmers to overcome these and other obstacles. There are plenty of tools out there, but JOMA can provide a real service by organizing them in a centralized location and by helping developers to construct these tools in a manner that will make them easier to use.

## Java Component Technology

What are some of the considerations for constructing reusable software? The best example of reusable software is provided by Sun. Consider the components that come with the Java Development Kit, such as the TextField or the Panel (or the Swing versions JTextField and JPanel). Sun has obviously put a lot of thought into their construction. They share a number of different characteristics.

1. They are versatile.
2. They are well documented.
3. It is not necessary to read the source code in order to use them -- all interaction is done with well-documented methods.

To achieve the same goals with mathematical components, it will be necessary to anticipate future uses of them as they are being designed. However, it is important that any single component not try to do too much. For example, in my opinion, a graphing calculator would not be an appropriate component -- it does too much. In the object-oriented spirit, it would be better to split the graphing calculator concept into its separate objects and design components to model each one. There should be something to play the role of the display screen, some way to provide input, and some way to do the computation. Those are all separate tasks and should be performed by separate components. Each of these components could then be used in other applications that didn't require all the power of a graphing calculator.

Finally, there is one other characteristic that many of Sun's components share: They are compatible with all visual development environments. This means, for example, that most of the properties of the components can be adjusted at design time by making entries into a property list. It also means that they can be manipulated with a drag-and-drop interface, and their dimensions can be adjusted with a mouse. Our mathematical components can achieve this by conforming to the JavaBeans standard. Chung and Digiano, in a future article in JOMA, will discuss the JavaBeans standard. In order to be the most useful, I would propose that most -- but not all -- mathematical components be JavaBeans.

Which components should be JavaBeans? Basically, a component should be a JavaBean if it would be useful to manipulate it using the tools of a visual development environment. This would be true for almost any visual component (such as a Panel or a TextArea), but it might also be true for non-visual components which might have properties that could be adjusted using the property list of the development environment. For example, in the MathToolkit discussed below there is a SymbolicFunction class that defines a function according to a formula. While this is an invisible component, it is a JavaBean because it is convenient to enter the formula from the property list. (There is actually another advantage: By making it a JavaBean, it can easily be added as a property to other JavaBeans, such as a table or a graph.) On the other hand, an equation parser, while a useful component, would not make a good JavaBean, because it is not visual and does not have any properties that need to be adjusted using the property list.

## A Unified Vision

If we accept that there is a need for reusable mathematical components, what is the best way to achieve this, and how can JOMA help? One possibility is to allow JOMA to serve as a marketplace for a wide variety of components with developers submitting them for review just as they do with Mathlets. This can easily be done and probably should be done. However, I have a proposal for an effort that could take place in parallel with this. My proposal is that we set up an open-source collaborative effort to construct a single Mathematics Toolkit that would contain one or more packages of components and files that would serve the most common needs of mathematical Java Developers. There are a number of advantages to this approach. Before discussing them it may be useful to see an illustration. In a joint project with Chuck Pheatt, funded by an NSF grant (DUE-9950714), I have developed a prototype for such a toolkit. (Almost all the components in the toolkit are based on Swing components so, in order to view the applet, you will need to make sure that your browser is Swing compliant.)

[Demonstration of some MathToolkit Components](http://www.maa.org/sites/default/files/images/upload_library/4/vol1/toolkitjava/JOMA/JOMADemo.htm)

In addition to the components in the demonstration, the MathToolkit contains a number of other components, including a FunctionTable and a ParametricTable, that are similar to the MathTable except that they accept as properties one or more functions or parametric equations and display the values on a table. There are beans that facilitate the creation of parametric curves for display on the MathGrapher and one that creates a random piecewise linear function that is useful for constructing examples. Other beans aid in the construction of secant lines and tangent lines for curves. MathToolkitZip contains the MathToolkit, documentation, source code, and tutorials for using it with JBuilder.

One of the advantages of having such a toolkit is that, in one download, the developer can get tools for accomplishing the most common tasks with an assurance that they will all be compatible with one another. In addition, by putting everything into one jar file it is possible to avoid duplication of efforts. For example, the MathToolkit contains a MathUtility class that contains a number of static methods used by many of the components, and the MathTextField is used in a number of the components.

By coordinating the construction of the components it will be possible to provide a consistent appearance across all components. Finally, if the MathToolkit should achieve the level of a standard, the very fact that many developers use it extensively would give some assurance of reliability.

If the development of the Mathematics Toolkit is done in parallel with the submission of individual components, some of the best of those could be considered for incorporation into the Toolkit.

I'm sure that there are others who have developed similar tools, and I am not suggesting that mine is the best. For example, some might argue that the reliance on Swing components is a drawback for the moment. What I am suggesting is that such an effort is worthwhile, and we should begin a discussion on how to achieve it.

## Links 

* [MathToolKit Documentation](http://www.maa.org/sites/default/files/images/upload_library/4/vol1/toolkitjava/JOMA/javadocs/mathtoolkit/packages.html)
* [Joe Yanik Homepage](http://www.emporia.edu/~hyanik/)
* [JOMA - Journal of Online Mathematics and its Applications](http://www.maa.org/loci-category/joma)
* [Original version of this README from MAA website](http://www.maa.org/press/periodicals/loci/joma/a-math-toolkit-for-java-developers)

## License
MathToolkit for Java

|                      |                                             |
|:---------------------|:--------------------------------------------|
| **Author:**          | Joe Yanik (<yanikjoe@emporia.edu>)          |
| **Copyright:**       | Copyright (c) 2001 Emporia State University |
| **License:**         | GNU General Public License, Version 3       |


The MathToolkit is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

The MathToolkit is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with the MathToolkit. If not, see http://www.gnu.org/licenses/.

