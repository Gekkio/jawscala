jawscala
=================

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

### <a name="jawscala-zk-async">jawscala-zk-async</a>

Asynchronous server push implementation based on [Atmosphere](https://github.com/Atmosphere/atmosphere)

### <a name="jawscala-zk-gritter">jawscala-zk-gritter</a>

Growl-like notifications (uses [Gritter for jQuery](https://github.com/jboesch/Gritter))

#### Usage

##### Gritter

        import fi.jawsy.jawscala.zk.gritter.Gritter

Display a simple notification:

        Gritter.add(title = "My title", text = "My text")

Display a sticky notification:

        Gritter.add(title = "My title", text = "My text", sticky = true)

Display a notification with image (supports ZK-style URLs, for example `~./classpath_image.png`):

        Gritter.add(title = "My title", text = "My text", image = "/image.png")

Display a notification for x milliseconds:

        Gritter.add(title = "My title", text = "My text", time = 9000)

Display a notification with a custom CSS class:

        Gritter.add(title = "My title", text = "My text", sclass = "error")

Removing all notifications from screen (including sticky ones):

        Gritter.removeAll()
