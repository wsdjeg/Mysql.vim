let s:save_cpo = &cpo
set cpo&vim

if exists('g:mysql_loaded')
    finish
endif
if !executable('mvn')&&!executable('javac')
    echohl WarningMsg | echom "Mysql.vim need javac or maven in the PATH" | echohl None
    finish
endif

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
command! -nargs=0
            \ SQLDebug
            \ call mysqlvim#utils#GetDMessage()
command! -nargs=0
            \ SQLDebugEmpty
            \ call mysqlvim#utils#EmptyDMessage()

let g:mysql_loaded = 1

let &cpo = s:save_cpo
unlet s:save_cpo
