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
    call jobstart(['mvn','-f',g:Mysql_vim_Home . join(['','libs','mysqlvim','','pom.xml'],'/'),'compile'], s:compile_lib_callbacks)
endf

fu! s:Compile_lib_JobHandler(job_id,data,event) abort
    if a:event == 'stdout'
        for msg in filter(a:data,'v:val !~ "^\\s*$"')
            echomsg msg
        endfor
    elseif a:event == 'stderr'
        let str = ' stderr: '.join(a:data)
        for msg in filter(a:data,'v:val !~ "^\\s*$"')
            echoerr msg
        endfor
    else
        if a:data == '0'
            echomsg "Mysqlvim libs builded successfully"
        else
            echomsg "Failed to build mysqlvim"
        endif
    endif
endfunction

let s:compile_lib_callbacks = {
            \ 'on_stdout': function('s:Compile_lib_JobHandler'),
            \ 'on_stderr': function('s:Compile_lib_JobHandler'),
            \ 'on_exit': function('s:Compile_lib_JobHandler')
            \ }
let &cpo = s:save_cpo
unlet s:save_cpo
