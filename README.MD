##Mysql.vim

vim plugin that support use mysql val vim

###commands

`SQLGetConnection` get the connection of mysql,need two args , uaername and password

`SQLUse` use database

`SQLCloseConnection` close the connection

`SQLCreate` create table example: `SQLCreate product price int(25) name char(255)`

`SQLDrop` drop table or database : `SQLDrop table test` or `SQLDrop database product`

`SQLInsert` insert date to table : `SQLInsert user age name 12 james`
