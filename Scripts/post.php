<?php

include 'db_connect.php';
$datetime = date('d/m/Y');
$request=$_REQUEST['getdata'];

 // customer Registration form
if($request=="UploadImage")
        {
        //mysqli_set_charset( $con, 'utf8');

        $image1 =$_REQUEST['insert_image_one'];

        $image2 =$_REQUEST['insert_image_two'];

        $image3 = $_REQUEST['insert_image_three'];

        $image4 = $_REQUEST['insert_image_four'];

        $imageName1="image1.jpg";
        $imageName2="image2.jpg";
        $imageName3="image3.jpg";
        $imageName4="image4.jpg";



            $Image1_path = "Uploads/$imageName1";
            $Image2_path = "Uploads/$imageName2";
            $Image3_path = "Uploads/$imageName3";
            $Image4_path = "Uploads/$imageName4";

            $actualpath = "http://192.168.43.107/DemoUploadTwoImage/$Image1_path";
            $actualpath1 = "http://192.168.43.107/DemoUploadTwoImage/$Image2_path";
            $actualpath2 = "http://192.168.43.107/DemoUploadTwoImage/$Image3_path";
            $actualpath3 = "http://192.168.43.107/DemoUploadTwoImage/$Image4_path";


                $m=mysqli_query($con,"INSERT INTO UserImage VALUES ('$actualpath','$actualpath1','$actualpath2','$actualpath3')");   

                if($m)
                {
                    file_put_contents($Image1_path,base64_decode($image1));
                    file_put_contents($Image2_path,base64_decode($image2));
                    file_put_contents($Image3_path,base64_decode($image3));
                    file_put_contents($Image4_path,base64_decode($image4));
                  $flag['Code']='Data Inserted';
                }
        print(json_encode($flag));    
        }
else
{

    $flag['Error']='2';
    print(json_encode($flag));      

}       
?>
9)