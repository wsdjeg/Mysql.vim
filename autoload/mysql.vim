if exists("g:Mysql_autoloaded")&&g:Mysql_autoloaded==1
    finish
else
    let g:Mysql_autoloaded=1
    let g:Mysql_vim_Home = fnamemodify(expand('<sfile>'), ':p:h:h:gs?\\?/?')
endif

fu! mysql#GetMysql_vim_classpath()
    if executable('mvn')
        let mysqlvimdir = g:Mysql_vim_Home.'libs/mysqlvim'
        let lines = split(system('mvn -f '.mysqlvimdir.'/pom.xml dependency:build-classpath'),'\n')
        for i in range(len(lines))
            if lines[i] =~ 'Dependencies classpath:'
                return lines[i+1]
            endif
        endfor
    endif
endf

function! mysql#GetConnection(...)
    let g:Mysql_vim_classpath = get(g:,'Mysql_vim_classpath',mysql#GetMysql_vim_classpath())
    let BaseCMD = 'java -cp '.g:Mysql_vim_classpath.' com.wsdjeg.mysqlvim.MysqlVi '
    let s:userinfo = split(a:000[0],' ')[0].' '.split(a:000[0],' ')[1]
    let cmd = s:BaseCMD
                \.'getconnection'
                \.' '
                \.s:userinfo
    if cmd != ''
        if split(system(cmd),'\n')[0]=='true'
            let g:JavaUnit_SQL_connected = 'true'
            echo 'Successfully connect to the database!'
        else
            let g:JavaUnit_SQL_connected = 'false'
            echo 'connected failed!'
        endif
    endif
endfunction

fu! javaunit#JavaUnit_CloseConnection()
    call s:closeconnection()
endfunction

function! javaunit#JavaUnit_SQL_Use(...)
    if s:hasSQLConnection()
        if a:1 == ''
            let s = input("please insert a databaseName?")
            echon "\r\r"
            echon ''
        else
            let s = a:000[0]
        endif
        if get(g:,'JavaUnit_SQL_Driver','')!=''
            let cmd = 'java -cp '
                        \.g:JavaUnit_SQL_Driver
                        \.':'
                        \.g:JavaUnit_tempdir
                        \.' com.wsdjeg.util.VimSqlUtils usedatabase '
                        \.s
                        \.' '
                        \.s:userinfo
        else
            let cmd = ''
        endif
        if cmd != ''
            if split(system(cmd),'\n')[0]=='true'
                echo 'success change to '.s
                let g:JavaUnit_SQL_DatabaseName = s
            else
                let input1 = input('database do not exists,create it (Y/N)? ')
                echon "\r\r"
                echon ''
                if input1 == 'Y'||input1 =='y'
                    let cmd = 'java -cp '
                                \.g:JavaUnit_SQL_Driver
                                \.':'
                                \.g:JavaUnit_tempdir
                                \.' com.wsdjeg.util.VimSqlUtils createdatabase '
                                \.s
                                \.' '
                                \.s:userinfo
                    if split(system(cmd),'\n')[0]=='true'
                        echo 'create success,change to '.s
                        let g:JavaUnit_SQL_DatabaseName = s
                    else
                        echo 'create failed!'
                    endif
                else
                    echo 'byby!'
                endif
            endif
        endif
    endif
endfunction

function! s:JavaUnit_SQL_drop_database(...)
    let input1 = input('try to delete '.a:1.' (Y/N)? ')
    echon "\r\r"
    echon ''
    if input1 == 'Y'||input1 =='y'
        let cmd = 'java -cp '
                    \.g:JavaUnit_SQL_Driver
                    \.':'
                    \.g:JavaUnit_tempdir
                    \.' com.wsdjeg.util.VimSqlUtils dropdatabase '
                    \.a:1
                    \.' '
                    \.s:userinfo
        if split(system(cmd),'\n')[0]=='true'
            echo 'delete success ! '
            let g:JavaUnit_SQL_DatabaseName = ''
        else
            echo 'no such database!'
        endif
    else
        echo 'byby!'
    endif
endf
function! s:JavaUnit_SQL_drop_table(...)
    if s:hasDatabaseName()
        let input1 = input('try to delete '.a:1.' (Y/N)? ')
        echon "\r\r"
        echon ''
        if input1 == 'Y'||input1 =='y'
            let cmd = 'java -cp '
                        \.g:JavaUnit_SQL_Driver
                        \.':'
                        \.g:JavaUnit_tempdir
                        \.' com.wsdjeg.util.VimSqlUtils droptable '
                        \.g:JavaUnit_SQL_DatabaseName
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

function! javaunit#JavaUnit_SQL_drop(...)
    if s:hasSQLConnection()
        if split(a:000[0],' ')[0]=='database'
            call s:JavaUnit_SQL_drop_database(split(a:000[0],' ')[1])
        elseif split(a:000[0],' ')[0]=='table'
            call s:JavaUnit_SQL_drop_table(split(a:000[0],' ')[1])
        else
            echo 'wrong input!'
        endif
    endif
endfunction

function! javaunit#JavaUnit_SQL_Insert(...)
    if s:hasSQLConnection()&&s:hasDatabaseName()
        let cmd = 'java -cp '
                    \.g:JavaUnit_SQL_Driver
                    \.':'
                    \.g:JavaUnit_tempdir
                    \.' com.wsdjeg.util.VimSqlUtils insert '
                    \.g:JavaUnit_SQL_DatabaseName
        for a in a:000
            let cmd .= ' '.a
        endfor
        "TODO
    endif
endfunction

function! s:hasDatabaseName()
    if get(g:,'JavaUnit_SQL_DatabaseName','')!=''
        return 1
    else
        echo 'please select a database!'
        return 0
    endif
endf

function! s:hasSQLConnection()
    if get(g:,'JavaUnit_SQL_connected','false')=='true'
        return 1
    else
        echo 'no connection!'
        return 0
    endif
endf

fu! s:closeconnection()
    if s:hasSQLConnection()
        call s:JavaUnit_unlet('g:JavaUnit_SQL_DatabaseName','g:JavaUnit_SQL_connected','s:userinfo')
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
