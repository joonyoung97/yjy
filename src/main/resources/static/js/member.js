function formCheck(form) {
var Inmate1 = $("#Inmate1");
var companionChoice1 = $("#companionChoice1");
var parentingExperience1 = $("#parentingExperience1");

        if($("#name").val() == "") {
            alert('이름을 입력하세요.');
            name.focus();
            return;
        }

        else if($("#email").val() == "") {
            alert('이메일을 입력하세요.');
            email.focus();
            return;
        }

        else if($("#password").val() == "") {
            alert('비밀번호를 입력하세요.');
            password.focus();
            return;
        }

        else if($("#date").val() == "") {
            alert('생년월일을 입력하세요.');
            date.focus();
            return;
        }

        else if($("#tel").val() == "") {
            alert('전화번호를 입력하세요.');
            tel.focus();
            return;
        }

        else if($("#address").val() == "") {
            alert('주소를 입력하세요.');
            address.focus();
            return;
        }

        else if($("#MBTI").val() == ""){
            alert("MBTI를 선택하세요.");
            MBTI.focus();
            return;
        }
        else if($(':radio[name="parentingExperience"]:checked').length < 1){
             alert("양육경험을 선택하세요.");
             parentingExperience1.focus();
             return;
        }
        else if($("#time_1").val() == ""){
            alert("출근시간을 입력하세요.");
            time_1.focus();
            return;
        }
        else if($("#time_2").val() == ""){
            alert("퇴근시간을 입력하세요.");
            time_2.focus();
            return;
        }
        else if($(':radio[name="Inmate"]:checked').length < 1){
            alert("동거인 유무를 선택하세요.");
            Inmate1.focus();
            return;
        }

        else if ($("input:checkbox[name='companionChoice']").is(":checked") == "") {
             alert("관심동물을 적어도 하나는 선택하여 주십시오.");
             companionChoice1.focus() ;
             return;
        }

        else if ($("input:checkbox[name='agree']").is(":checked") == "") {
            alert("약관동의하여주세요.");
            form.name.focus() ;
            event.preventDefault();
        }
        else {
        $("form").submit();
        alert('회원가입이 완료되었습니다.');
     }
}

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {

            var fullAddr = '';
            var extraAddr = '';

            if (data.userSelectedType === 'R') {
                fullAddr = data.roadAddress;

            } else {
                fullAddr = data.jibunAddress;
            }

            if(data.userSelectedType === 'R'){

                if(data.bname !== ''){
                    extraAddr += data.bname;
                }

                if(data.buildingName !== ''){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }

                fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
            }

            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById('sample6_address').value = fullAddr;

            document.getElementById('sample6_address2').focus();
        }
    }).open();
}

function selectAll(selectAll)  {
    const checkboxes
         = document.getElementsByName('agree');

    checkboxes.forEach((checkbox) => {
      checkbox.checked = selectAll.checked;
    })
  }

 const autoHyphen2 = (target) => {
      target.value = target.value
        .replace(/[^0-9]/g, '')
       .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
 }