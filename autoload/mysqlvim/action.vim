let s:save_cpo = &cpo
set cpo&vim

let s:action = {}
let s:action.type = ''
let s:action.userinfo = ''
let s:action.args = []
function! mysqlvim#action#get_action(...)
    if len(split(a:000,' '))==1
        let s:action.type = '--init'
    elseif len(split(a:000,' ')) >= 2
        let args = split(a:000,' ')
        let s:action.type = args[0]
        let s:action.userinfo = args[1].' '.args[2]
    endif
    return s:action
endfunction

function! mysqlvim#action#Get_connection(action) abort
    
endfunction

function! mysqlvim#action#execsql(sql) abort
    let result = ''
    return result
endfunction


let &cpo = s:save_cpo
unlet s:save_cpo
