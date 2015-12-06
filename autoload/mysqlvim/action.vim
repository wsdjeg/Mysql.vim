let s:action = {}
let s:action.type = ''
let s:action.userinfo = ''
let s:action.args = []
function! mysqlvim#action#get_action(...)
    if len(split(a:000,' '))==1
        let s:action.type = '--init'
        return s:action
    elseif len(split(a:000,' ')) >= 2

    endif
endfunction
