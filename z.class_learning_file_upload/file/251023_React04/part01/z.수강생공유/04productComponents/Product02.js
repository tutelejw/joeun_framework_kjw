
import { Link } from 'react-router-dom';

const Product02 = () => {

  console.log("Product02")

  return (

    <div className="ViewGood">
    
      <h2>Product02</h2>    
      Product02...
      
      <br/><br/>

      <Link to='/'>Main</Link> &nbsp; &nbsp; 
      <Link to='/Product/Product01'>Product01</Link>     

    </div>
  );
};

export default Product02;