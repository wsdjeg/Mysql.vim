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

let &cpo = s:save_cpo
unlet s:save_cpo
