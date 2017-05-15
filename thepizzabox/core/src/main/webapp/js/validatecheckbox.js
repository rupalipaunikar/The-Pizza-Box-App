function validateform()
{
    var checkboxes=document.getElementsByName("itemCheckBox");
    var okay=false;
    for(var i=0,l=checkboxs.length;i<l;i++)
    {
        if(checkboxs[i].checked)
        {
            okay=true;
            break;
        }
    }
    if(okay)return;
    else alert("Please check a checkbox");
}