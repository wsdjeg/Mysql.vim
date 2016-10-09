let s:save_cpo = &cpo
set cpo&vim

let s:action = {}
let s:action.type = ''
let s:action.userinfo = ''
let s:action.args = []
function! mysql#action#get_action(...)
    if len(a:000) == 1
        let s:action.type == a:1
    elseif len(a:000) >= 4
    endif
    return s:action.type
endfunction

function! mysql#action#Get_connection(action) abort
endfunction

function! mysql#action#execsql(sql) abort
    let result = ''
    return result
endfunction


let &cpo = s:save_cpo
unlet s:save_cpo
