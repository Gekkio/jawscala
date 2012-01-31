Jawscala: Jawsy Solutions libraries for Scala 2.9+
==================================================

## Introduction

Jawscala is a library project with useful tools for Scala developers who use some commonly used Java frameworks.

## Modules

### <a name="jawscala-vaadin">jawscala-vaadin</a>

Useful things for developers using [Vaadin](http://vaadin.com).

#### Features

+ (Mostly implicit) conversions from functions to commonly used single-method interfaces in Vaadin

#### Usage

1. Import everything

        import fi.jawsy.jawscala.vaadin.Vaadin._

2. Enjoy implicit conversions

        // No need for anonymous classes when implicits have been imported
        // The type of e determines the listener type here
        myButton.addListener { e: Button#ClickEvent =>
          println("Profit!")
        }

### <a name="jawscala-zk">jawscala-zk</a>

Useful things for developers using [ZK](http://www.zkoss.org).
