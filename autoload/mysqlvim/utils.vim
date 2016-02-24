let s:save_cpo = &cpo
set cpo&vim

let g:mysqlvim#utils#DMessage = ''

function! mysqlvim#utils#Debug(agrs) abort
    let g:mysqlvim#utils#DMessage .= a:agrs
endfunction

function! mysqlvim#utils#GetDMessage() abort
    echomsg g:mysqlvim#utils#DMessage
endfunction

function! mysqlvim#utils#EmptyDMessage() abort
    unlet g:mysqlvim#utils#DMessage
    let g:mysqlvim#utils#DMessage = ''
    echo 'Empty Debug Message successful!'
endfunction

function! mysqlvim#utils#args_analyzer(agrs)

endfunction

fu! mysqlvim#utils#compilelib()
    exec "!mvn -f " . g:Mysql_vim_Home . join(['','libs','mysqlvim','','pom.xml'],'/') .' clean compile'
    let job2 = jobstart(['mvn','-f',g:Mysql_vim_Home . join(['','libs','mysqlvim','','pom.xml'],'/'),'pom.xml'], s:callbacks)
endf

let s:callbacks = {
            \ 'on_stdin':function('mysqlvim#job#Compile_lib_Handler'),
            \ 'on_stdout': function('mysqlvim#job#Compile_lib_Handler'),
            \ 'on_stderr': function('mysqlvim#job#Compile_lib_Handler'),
            \ 'on_exit': function('mysqlvim#job#Compile_lib_Handler')
            \ }
let &cpo = s:save_cpo
unlet s:save_cpo
