
import User01 from './User01';
import User02 from './User02';
import User03 from './User03';
import CommonTop from '../commonComponet/CommonTop';

const User = () => {

  console.log("User");

  return (

    <div className="ViewGood">

      {/* 회원관리 메뉴 쓰이는 공통,동일한 화면을 구성을 원하면... */}
      <CommonTop/>

      <User01/>
      <User02/>
      <User03/>
    
    </div>

  );
};

export default User;