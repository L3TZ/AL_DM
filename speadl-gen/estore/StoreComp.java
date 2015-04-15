package estore;

import interfaces.IBankTransfert;
import interfaces.IJustHaveALook;
import interfaces.IProvider;
import interfaces.IStoreFastLane;
import interfaces.IStoreLane;

@SuppressWarnings("all")
public abstract class StoreComp {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IBankTransfert payment();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IProvider provider();
  }
  
  public interface Component extends StoreComp.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IJustHaveALook giveConsult();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IStoreFastLane express();
    
    /**
     * This can be called to access the provided port.
     * 
     */
    public IStoreLane classic();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements StoreComp.Component, StoreComp.Parts {
    private final StoreComp.Requires bridge;
    
    private final StoreComp implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_giveConsult() {
      assert this.giveConsult == null: "This is a bug.";
      this.giveConsult = this.implementation.make_giveConsult();
      if (this.giveConsult == null) {
      	throw new RuntimeException("make_giveConsult() in estore.StoreComp should not return null.");
      }
    }
    
    private void init_express() {
      assert this.express == null: "This is a bug.";
      this.express = this.implementation.make_express();
      if (this.express == null) {
      	throw new RuntimeException("make_express() in estore.StoreComp should not return null.");
      }
    }
    
    private void init_classic() {
      assert this.classic == null: "This is a bug.";
      this.classic = this.implementation.make_classic();
      if (this.classic == null) {
      	throw new RuntimeException("make_classic() in estore.StoreComp should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_giveConsult();
      init_express();
      init_classic();
    }
    
    public ComponentImpl(final StoreComp implem, final StoreComp.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private IJustHaveALook giveConsult;
    
    public IJustHaveALook giveConsult() {
      return this.giveConsult;
    }
    
    private IStoreFastLane express;
    
    public IStoreFastLane express() {
      return this.express;
    }
    
    private IStoreLane classic;
    
    public IStoreLane classic() {
      return this.classic;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private StoreComp.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected StoreComp.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IJustHaveALook make_giveConsult();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IStoreFastLane make_express();
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IStoreLane make_classic();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected StoreComp.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected StoreComp.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized StoreComp.Component _newComponent(final StoreComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of StoreComp has already been used to create a component, use another one.");
    }
    this.init = true;
    StoreComp.ComponentImpl  _comp = new StoreComp.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
