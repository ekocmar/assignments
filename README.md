# Assignments

This project is prepared for the assignment from ComeOn.

Project contains various endpoint corresponding to the given assignment. Few of the endpoints and sample formats are explained below.

#PlayerController endpoints
**/ws/v0/player/create** this endpoint is used to create new players and accepts application/json 
example format:
{
	"name":"Gimli",
	"surname":"Moria"
}

**/ws/v0/player** this endpoint is used to query players. example parameter usage:  /ws/v0/player?id=1&name=Gimli

#GameController endpoints
**/ws/v0/game/create** this endpoint used to create new games and accepts application/json
example format:
{
	"name":"PUBG",
	"genre":"Battle Royale",
	"manufacturer":"PUBG Corporation"
}

**/ws/v0/game** this endpoint is used to query games. example parameter usage: /ws/v0/game?id=3&name=Roulette

#GameLoveController endpoints
**/ws/v0/lovedGames** this endpoint is used to query all loved games if no params provided.
If top parameter provided, it returns top x most loved games together with loved count.
example parameter usage /ws/v0/lovedGames?top=5

**/ws/v0/player/{playerId}/lovedGames** this endpoint is used to query the loved games of the given playerId.
example usage: /ws/v0/player/1/lovedGames

**/ws/v0/love** this endpoint is used to make a given player to love a given game.
example usage: /ws/v0/love?playerId=1&gameId=5

**/ws/v0/unlove** this endpoint is used to make a given player to unlove a given game.
example usage: /ws/v0/unlove?playerId=1&gameId=5

# Extra info
extra info can be found on javadoc descriptions. 
postman collection:https://www.getpostman.com/collections/837455e12a0879589338