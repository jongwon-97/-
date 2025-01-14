<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <style>
       .header,.footer{
           display:none;
       }
       #userId{
           padding:4px;
           border:1px solid #ddd;

       }
    </style>
    <script>
        const check = function(){
        if(!idf.userId.value){
            alert('아이디를 입력하세요');
            idf.userId.focus();
            return false;
          }
        return true;
      }

      const setId = function(uid){
        //부모창의 userId input 텍스트에 uid 값을 설정하자
        //window > document > form > input,select,textarea,...
        //opener <== 부모창(window)
        opener.document.forms[0].userId.value=uid;

        //팝업창 닫기
        self.close();

      }

    </script>
    <div class="wrap">

        <form name="idf" action="idCheck" method="post"
        onsubmit="return check()">
            <label for="userId">
                아이디
            </label>
            <input type="test" name="userId" id="userId"
            placeholder="ID" autofocus="autofocus">
            <button type="submit" class="id-check-button">확 인</button>
        </form>
        <div id="idResult">
             <h3 style="color:red">${msg}</h3>

             <c:if test="${result=='ok'}" >
                  <button  class="id-check-button" onclick="setId('${userId}')">아이디 사용하기</button>
             </c:if>
        </div>


    </div>