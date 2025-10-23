
import { Link } from 'react-router-dom';

const Header = () => {

  console.log("Header")

  return (

    <div className="ViewGood">

      <Link to='/'>Main</Link> 
      &nbsp; &nbsp; 
      <Link to='/user'>User</Link>
      &nbsp; &nbsp; 
      <Link to='/product'>Product</Link>
      &nbsp; &nbsp; 
      <Link to='/purchase/user03'>Purchase</Link>
      &nbsp; &nbsp; 
      <Link to='/etc'>Etc</Link>
      &nbsp; &nbsp; 
      <Link to='/login'>Login</Link>
      
    </div>

  );
};

export default Header;