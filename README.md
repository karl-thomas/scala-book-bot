# Scala Book Bot
Scala Book Bot is a program to help manage a book club channel on slack

### Dependencies

This project was built using:

| dependency | version|
|---|---|
| Scala  | 2.13.1  |
| SBT  |  1.3.8 |


## Setup

#### Clone down the project

```
git clone https://github.com/karl-thomas/scala-book-bot.git
```

#### Environment Setup
You will need to register with the Google Books API and get an API token and place it in a file named `.env` in the root of the project after cloning. For naming see the `.env.example` file, also the root of this project.

#### Ensure the project compiles
```
sbt compile
```


## Testing

After doing the setup above, you should be able to run `sbt test` to run the tests successfully.
