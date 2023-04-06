function formCheck(form) {
    if(text.value == "") {
        alert('이름을 입력하세요.');
        form.name.focus() ;
        return ;
    }

    var patt = new RegExp("[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}");

    if( !patt.test( $("#tlno").val()) ){
        alert("전화번호를 정확히 입력하여 주십시오.");
        return false;
    }
}

const autoHyphen2 = (target) => {
      target.value = target.value
        .replace(/[^0-9]/g, '')
       .replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
 }