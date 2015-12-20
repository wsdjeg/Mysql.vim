command! -nargs=*
            \ SQLGetConnection
            \ call mysql#GetConnection(<q-args>)
command! -nargs=0
            \ SQLCloseConnection
            \ call mysql#CloseConnection()
command! -nargs=*
            \ SQLUse
            \ call mysql#SQL_Use(<q-args>)
command! -nargs=*
            \ SQLCreate
            \ call mysql#SQL_Create(<q-args>)
command! -nargs=*
            \ SQLDrop
            \ call mysql#SQL_drop(<q-args>)
command! -nargs=*
            \ SQLInsert
            \ call mysql#SQL_Insert(<q-args>)
command! -nargs=*
            \ SQLSelect
            \ call mysql#SQL_Select(<q-args>)
command! -nargs=*
            \ SQLQuery
            \ call mysql#SQL_Query(<q-args>)
