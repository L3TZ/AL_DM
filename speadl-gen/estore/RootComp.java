package estore;

import banking.BankComp;
import estore.ClientComp;
import estore.ProviderComp;
import estore.StoreComp;
import interfaces.IBankTransfert;
import interfaces.IJustHaveALook;
import interfaces.IProvider;
import interfaces.IStoreFastLane;
import interfaces.IStoreLane;

@SuppressWarnings("all")
public abstract class RootComp {
  public interface Requires {
  }
  
  public interface Component extends RootComp.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public Runnable go();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ClientComp.Component cl();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public StoreComp.Component st();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public ProviderComp.Component pv();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public BankComp.Component bk();
  }
  
  public static class ComponentImpl implements RootComp.Component, RootComp.Parts {
    private final RootComp.Requires bridge;
    
    private final RootComp implementation;
    
    public void start() {
      assert this.cl != null: "This is a bug.";
      ((ClientComp.ComponentImpl) this.cl).start();
      assert this.st != null: "This is a bug.";
      ((StoreComp.ComponentImpl) this.st).start();
      assert this.pv != null: "This is a bug.";
      ((ProviderComp.ComponentImpl) this.pv).start();
      assert this.bk != null: "This is a bug.";
      ((BankComp.ComponentImpl) this.bk).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_cl() {
      assert this.cl == null: "This is a bug.";
      assert this.implem_cl == null: "This is a bug.";
      this.implem_cl = this.implementation.make_cl();
      if (this.implem_cl == null) {
      	throw new RuntimeException("make_cl() in estore.RootComp should not return null.");
      }
      this.cl = this.implem_cl._newComponent(new BridgeImpl_cl(), false);
      
    }
    
    private void init_st() {
      assert this.st == null: "This is a bug.";
      assert this.implem_st == null: "This is a bug.";
      this.implem_st = this.implementation.make_st();
      if (this.implem_st == null) {
      	throw new RuntimeException("make_st() in estore.RootComp should not return null.");
      }
      this.st = this.implem_st._newComponent(new BridgeImpl_st(), false);
      
    }
    
    private void init_pv() {
      assert this.pv == null: "This is a bug.";
      assert this.implem_pv == null: "This is a bug.";
      this.implem_pv = this.implementation.make_pv();
      if (this.implem_pv == null) {
      	throw new RuntimeException("make_pv() in estore.RootComp should not return null.");
      }
      this.pv = this.implem_pv._newComponent(new BridgeImpl_pv(), false);
      
    }
    
    private void init_bk() {
      assert this.bk == null: "This is a bug.";
      assert this.implem_bk == null: "This is a bug.";
      this.implem_bk = this.implementation.make_bk();
      if (this.implem_bk == null) {
      	throw new RuntimeException("make_bk() in estore.RootComp should not return null.");
      }
      this.bk = this.implem_bk._newComponent(new BridgeImpl_bk(), false);
      
    }
    
    protected void initParts() {
      init_cl();
      init_st();
      init_pv();
      init_bk();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final RootComp implem, final RootComp.Requires b, final boolean doInits) {
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
    
    public Runnable go() {
      return this.cl().go();
    }
    
    private ClientComp.Component cl;
    
    private ClientComp implem_cl;
    
    private final class BridgeImpl_cl implements ClientComp.Requires {
      public final IJustHaveALook needConsult() {
        return RootComp.ComponentImpl.this.st().giveConsult();
      }
      
      public final IStoreFastLane fast() {
        return RootComp.ComponentImpl.this.st().express();
      }
      
      public final IStoreLane cool() {
        return RootComp.ComponentImpl.this.st().classic();
      }
    }
    
    public final ClientComp.Component cl() {
      return this.cl;
    }
    
    private StoreComp.Component st;
    
    private StoreComp implem_st;
    
    private final class BridgeImpl_st implements StoreComp.Requires {
      public final IProvider provider() {
        return RootComp.ComponentImpl.this.pv().providing();
      }
      
      public final IBankTransfert payment() {
        return RootComp.ComponentImpl.this.bk().bankTransfer();
      }
    }
    
    public final StoreComp.Component st() {
      return this.st;
    }
    
    private ProviderComp.Component pv;
    
    private ProviderComp implem_pv;
    
    private final class BridgeImpl_pv implements ProviderComp.Requires {
    }
    
    public final ProviderComp.Component pv() {
      return this.pv;
    }
    
    private BankComp.Component bk;
    
    private BankComp implem_bk;
    
    private final class BridgeImpl_bk implements BankComp.Requires {
    }
    
    public final BankComp.Component bk() {
      return this.bk;
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
  
  private RootComp.ComponentImpl selfComponent;
  
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
  protected RootComp.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RootComp.Requires requires() {
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
  protected RootComp.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ClientComp make_cl();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract StoreComp make_st();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract ProviderComp make_pv();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract BankComp make_bk();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized RootComp.Component _newComponent(final RootComp.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RootComp has already been used to create a component, use another one.");
    }
    this.init = true;
    RootComp.ComponentImpl  _comp = new RootComp.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RootComp.Component newComponent() {
    return this._newComponent(new RootComp.Requires() {}, true);
  }
}
