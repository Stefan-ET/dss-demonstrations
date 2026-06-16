updateSignatureLevel("CBAdES");

function updateSignatureLevel(signatureForm) {
    $('#selectSignatureLevel').empty();

    var process = $('#process').val();
    if ($('#nexu_ready_alert').is(':hidden')) {
        process += "_SERVER_SIGN";
    }

    $.ajax({
        type : "GET",
        url : "data/levelsByForm?form=" + signatureForm + "&process=" + process,
        dataType : "json",
        error : function(msg) {
            alert("Error !: " + msg);
        },
        success : function(data) {
            $.each(data, function(idx) {
                $('#selectSignatureLevel').append($('<option>', {
                    value: data[idx],
                    text: data[idx]
                }));
            });
        }
    });
}