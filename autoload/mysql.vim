if exists("g:Mysql_autoloaded")&&g:Mysql_autoloaded==1
    finish
else
    let g:Mysql_autoloaded=1
    let g:Mysql_vim_Home = fnamemodify(expand('<sfile>'), ':p:h:h:gs?\\?/?')
endif

fu! mysql#GetMysql_vim_classpath()
    if executable('mvn')
        let mysqlvimdir = g:Mysql_vim_Home.'/libs/mysqlvim'
        let lines = split(system('mvn -f '.mysqlvimdir.'/pom.xml dependency:build-classpath'),'\n')
        for i in range(len(lines))
            if lines[i] =~ 'Dependencies classpath:'
                return lines[i+1].':'.mysqlvimdir.'/target/classes'
            endif
        endfor
    endif
endf

function! s:mysqlargsinit()
    let cmd = s:BaseCMD . '--init'
    let lines = split(system(cmd),'\n')
    let s:mysqlproperties={}
    for line in lines
        let s:mysqlproperties[split(line,'==')[0]]=split(line,'==')[1]
    endfor
endfunction

function! mysql#GetConnection(...)
    if !exists('g:Mysql_vim_classpath')
        let g:Mysql_vim_classpath = mysql#GetMysql_vim_classpath()
    endif
    let s:BaseCMD = 'java -cp '.g:Mysql_vim_classpath.' com.wsdjeg.mysqlvim.MysqlVi '
    call s:mysqlargsinit()
    let s:userinfo = split(a:000[0],' ')[0].' '.split(a:000[0],' ')[1]
    let cmd = s:BaseCMD
                \.get(s:mysqlproperties,'LOGIN')
                \.' '
                \.s:userinfo
    if cmd != ''
        if split(system(cmd),'\n')[0]=='true'
            let g:Mysql_SQL_connected = 'true'
            echo 'Successfully connect to the database!'
        else
            let g:Mysql_SQL_connected = 'false'
            echo 'connected failed!'
        endif
    endif
endfunction

fu! mysql#CloseConnection()
    call s:closeconnection()
endfunction

fu! mysql#SQL_Create(...)
    if s:hasSQLConnection()&&s:hasDatabaseName()
        if len(a:000)%2==1
            let cmd = s:BaseCMD
                        \.get(s:mysqlproperties,'CREATETABLE')
                        \.' '
                        \.g:Mysql_SQL_DatabaseName
                        \.' '
                        \.s:userinfo
                        \.' '
            for a in a:000
                let cmd .= a.' '
            endfor
            let cmd = substitute(cmd,'(','\\(','g')
            let cmd = substitute(cmd,')','\\)','g')
            let out_put = system(cmd)
            if out_put != ''
                if split(out_put,'\n')[0]=='true'
                    echo 'create table success!'
                else
                    echo 'failed!'
                endif
            endif
        else
            echo 'args numbers error!'
        endif
    endif
endf

function! mysql#SQL_Query(...) abort
    if s:hasSQLConnection() && s:hasDatabaseName()
    endif
endfunction

function! mysql#SQL_Use(...)
    if s:hasSQLConnection()
        if a:1 == ''
            let s = input("please insert a databaseName : ")
            echon "\r\r"
            echon ''
        else
            let s = a:000[0]
        endif
        if s!=''
            let cmd = s:BaseCMD
                        \.get(s:mysqlproperties,'USE')
                        \.' '
                        \.s
                        \.' '
                        \.s:userinfo
            let out_put = system(cmd)
            if out_put != ''
                if split(out_put,'\n')[0]=='true'
                    echo 'success change to '.s
                    let g:Mysql_SQL_DatabaseName = s
                else
                    let input1 = input('database do not exists,create it (Y/N)? ')
                    echon "\r\r"
                    echon ''
                    if input1 == 'Y'||input1 =='y'
                        let cmd = s:BaseCMD
                                    \.get(s:mysqlproperties,'CREATEDATABASE')
                                    \.' '
                                    \.s
                                    \.' '
                                    \.s:userinfo
                        if split(system(cmd),'\n')[0]=='true'
                            echo 'create success,change to '.s
                            let g:Mysql_SQL_DatabaseName = s
                        else
                            echo 'create failed!'
                        endif
                    else
                        echo 'byby!'
                    endif
                endif
            endif
        else
            echo 'databaseName should not be empty!'
        endif
    endif
endfunction

function! s:Mysql_SQL_drop_database(...)
    let input1 = input('try to delete '.a:1.' (Y/N)? ')
    echon "\r\r"
    echon ''
    if input1 == 'Y'||input1 =='y'
        let cmd = s:BaseCMD
                    \.get(s:mysqlproperties,'DROPDATABASE')
                    \.' '
                    \.a:1
                    \.' '
                    \.s:userinfo
        if split(system(cmd),'\n')[0]=='true'
            echo 'delete success ! '
            let g:Mysql_SQL_DatabaseName = ''
        else
            echo 'no such database!'
        endif
    else
        echo 'byby!'
    endif
endf
function! s:Mysql_SQL_drop_table(...)
    if s:hasDatabaseName()
        let input1 = input('try to delete '.a:1.' (Y/N)? ')
        echon "\r\r"
        echon ''
        if input1 == 'Y'||input1 =='y'
            let cmd = s:BaseCMD
                        \.get(s:mysqlproperties,'DROPTABLE')
                        \.' '
                        \.g:Mysql_SQL_DatabaseName
                        \.' '
                        \.a:1
                        \.' '
                        \.s:userinfo
            if split(system(cmd),'\n')[0]=='true'
                echo 'delete success ! '
            else
                echo 'no such table!'
            endif
        else
            echo 'byby!'
        endif
    endif
endf

function! mysql#SQL_drop(...)
    if s:hasSQLConnection()
        if split(a:000[0],' ')[0]=='database'
            call s:Mysql_SQL_drop_database(split(a:000[0],' ')[1])
        elseif split(a:000[0],' ')[0]=='table'
            call s:Mysql_SQL_drop_table(split(a:000[0],' ')[1])
        else
            echo 'wrong input!'
        endif
    endif
endfunction

function! mysql#SQL_Insert(...)
    if s:hasSQLConnection()&&s:hasDatabaseName()
        let cmd = s:BaseCMD
                    \.get(s:mysqlproperties,'INSERT')
                    \.' '
                    \.g:Mysql_SQL_DatabaseName
                    \.' '
                    \.s:userinfo
                    \.' '
        for a in a:000
            let cmd .= ' '.a
        endfor
        let out_put =split(system(cmd),'\n')
        if out_put[0]=='true'
            echo 'insert success!'
        else
            echo 'failed!'
        endif
    endif
endfunction

fu! mysql#SQL_Select(...)
    if s:hasSQLConnection()&&s:hasDatabaseName()
        let cmd = s:BaseCMD
                    \.get(s:mysqlproperties,'SELECT')
                    \.' '
                    \.g:Mysql_SQL_DatabaseName
                    \.' '
                    \.s:userinfo
                    \.' '
        "TODO
    endif
endf

function! s:hasDatabaseName()
    if get(g:,'Mysql_SQL_DatabaseName','')!=''
        return 1
    else
        echo 'please select a database!'
        return 0
    endif
endf

function! s:hasSQLConnection()
    if get(g:,'Mysql_SQL_connected','false')=='true'
        return 1
    else
        echo 'no connection!'
        return 0
    endif
endf

fu! s:closeconnection()
    if s:hasSQLConnection()
        call s:JavaUnit_unlet('g:Mysql_SQL_DatabaseName','g:Mysql_SQL_connected','s:userinfo')
    endif
endf
fu! s:JavaUnit_unlet(...)
    for a in a:000
        if exists(a)
            exec 'unlet '.a
        endif
    endfor
    echo 'connection has been closed!'
endf
