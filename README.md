#1To humbly transform the design.


The constraints imposed by legacy systems and third party systems often turn the situations very interesting.  Engagements with such systems many a times remind me of the Serenity Prayer. 

Here is one more interesting situation, where I accepted the behavior of the legacy system as it is, but devised a layer of facade to offer the required behavior.


The context

We are allowed to access a repository of Tasks through its service interfaces.

The repository provides two functional interfaces. One is to request a task from the repository and the other is to return the task back to the repository.

The task can be either in one of two states. An Assignable state or in Hold state. 

The constraint

You can’t afford to ask for a task of your choice from the repository. It is more like you are forced to pick a random chocolate from an assortment of chocolates. 

The requirement

The requirement is that when a user requests for a task, we need to offer the task that is in an Assignable state to the user. 
