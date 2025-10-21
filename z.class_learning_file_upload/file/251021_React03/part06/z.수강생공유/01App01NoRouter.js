import './App.css';

import Component01 from './01components/Component01';
import Component02 from './01components/Component02';
import Component03 from './01components/Component03';

function App() {

  console.log("App");

  return (
    
    <div className="ViewGood">
      
      <h1>01 React Router Dom</h1>

      <Component01/>
      <Component02/>
      <Component03/>

    </div>

  );
}

export default App;
