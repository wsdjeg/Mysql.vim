let s:save_cpo = &cpo
set cpo&vim

fu! mysqlvim#job#Compile_lib_Handler(job_id,data,event) abort
    if a:event == 'stdout'
        let str = ' stdout: '.join(a:data)
        echomsg str
    elseif a:event == 'stderr'
        let str = ' stderr: '.join(a:data)
        echomsg str
    elseif a:event == 'stdin'
        echo a:data
    else
        let str = ' exited'
    endif

endf

function! s:JobHandler(job_id,data,event) abort
    if a:event == 'stdout'
        let str = ' stdout: '.join(a:data)
        echomsg str
    elseif a:event == 'stderr'
        let str = ' stderr: '.join(a:data)
        echomsg str
    elseif a:event == 'stdin'
        echo a:data
    else
        let str = ' exited'
    endif
endfunction

let s:callbacks = {
            \ 'on_stdin':function('s:JobHandler'),
            \ 'on_stdout': function('s:JobHandler'),
            \ 'on_stderr': function('s:JobHandler'),
            \ 'on_exit': function('s:JobHandler')
            \ }
"let job1 = jobstart(['bash'], extend({'shell': 'shell 1'}, s:callbacks))
"let job2 = jobstart(['java','-cp','/home/wsdjeg/.vim/bundle/Mysql.vim','Foo'], s:callbacks)
let &cpo = s:save_cpo
unlet s:save_cpo
