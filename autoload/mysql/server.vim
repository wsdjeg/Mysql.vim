let s:save_cpo = &cpoptions
set cpoptions&vim


let s:request = {}

function! mysql#server#Communicate(id, data) abort
    let s:response = ''
    return s:response
endfunction




let &cpoptions = s:save_cpo
unlet s:save_cpo
