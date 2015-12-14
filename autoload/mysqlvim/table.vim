let s:save_cpo = &cpo
set cpo&vim
let s:tables = {}

function! mysqlvim#table#Add_table_names(tables) abort
    let tables = split(a:tables,' ')
    echo len(tables)
endfunction


let &cpo = s:save_cpo
unlet s:save_cpo
