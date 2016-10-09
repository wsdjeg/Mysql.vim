let s:save_cpo = &cpo
set cpo&vim


let s:request = {}

function! mysql#server#Getresponse()
    let s:response = {}
    return s:response
endfunction




let &cpo = s:save_cpo
unlet s:save_cpo
